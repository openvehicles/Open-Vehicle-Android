/*
    Installation:
     - Save as /store/scripts/lib/scheduled_booster.js
     - Add to /store/scripts/ovmsmain.js: scheduled_booster = require("lib/scheduled_booster");
     - Issue "script reload"
*/

// create config usr value
var init_usrcfg = OvmsConfig.Get("usr","b.init", "no");

if(init_usrcfg == "no"){
    OvmsConfig.Set("usr", "b.init", "yes");           // init usr config
    OvmsConfig.Set("usr", "b.activated", "no");       // schedule time activated     
    OvmsConfig.Set("usr", "b.scheduled", "0515");     // schedule time format 1400 for 14:00 / 2 p.m. o'clock 
    OvmsConfig.Set("usr", "b.scheduled_2", "no");     // double schedule time for 10 min.
    OvmsConfig.Set("usr", "b.week", "no");            // schedule time weekly at Day de/activated
    OvmsConfig.Set("usr", "b.day_start", "1");        // Day1 -> Monday
    OvmsConfig.Set("usr", "b.day_end", "6");          // Day5 -> Friday + 1 switched at midnight
    OvmsConfig.Set("usr", "b.ticker", "10");     	  // ticker format 1/10/60/300/600/3600 seconds
    OvmsConfig.Set("usr", "b.data", "0,0,0,0,-1,-1,-1"); // Data Array
}

var state = {
    start_day: false,       // start_day subscription
    end_day: false,         // end_day subscription
    scheduled_boost: false, // scheduled_boost subscription
    scheduled_boost_2: false, // scheduled_boost_2 subscription, boost two times
    booster_data: false,    // booster_data subscription
};

// get/set value
OvmsConfig.Delete("usr", "b.data");
var scheduled_ticker = 'ticker.' + OvmsConfig.Get("usr","b.ticker","30");

function veh_on() {
    return OvmsMetrics.Value("v.e.on");
}

function veh_hvac() {
    return OvmsMetrics.Value("v.e.hvac");
}

function start_day() {
    if (OvmsConfig.Get("usr","b.week") == "yes"){
        OvmsConfig.Set("usr", "b.activated", "yes");
    }
}

function end_day() {
    if (OvmsConfig.Get("usr","b.week") == "yes") {
        OvmsConfig.Set("usr", "b.activated", "no");
    }
}
// scheduled booster time
function scheduled_boost() {
    if(!veh_on() && !veh_hvac() && (OvmsConfig.Get("usr","b.activated") == "yes")) {
        OvmsVehicle.ClimateControl("on");
        if (OvmsConfig.Get("usr","b.scheduled_2") == "yes") {
            state.scheduled_boost_2 = PubSub.subscribe('ticker.60',scheduled_boost_2);
        }
    }
}
// scheduled booster time add a second booster time for 10 min. booster
function scheduled_boost_2() {
    if(!veh_on() && !veh_hvac()) {
        OvmsVehicle.ClimateControl("on");
    }
    PubSub.unsubscribe(state.scheduled_boost_2);
}

function booster_data() {

    var newdata = OvmsConfig.Get("usr", "b.data", "0,0,0,0,-1,-1,0").split(",");
    if (newdata[0] == 1) {
        // booster scheduled on
        if (newdata[1] == 1) {
            OvmsConfig.Set("usr", "b.activated", "yes");
            if(newdata[3] == 0){
                PubSub.unsubscribe(state.scheduled_boost);
                var ClockEvent = 'clock.' + OvmsConfig.Get("usr","b.scheduled");
                state.scheduled_boost = PubSub.subscribe(ClockEvent,scheduled_boost);
            }
        }
        // booster scheduled off
        if (newdata[1] == 2) {
            OvmsConfig.Set("usr", "b.activated", "no");            
        }
        // boost every day between start/end day on
        if (newdata[2] == 1) {
            OvmsConfig.Set("usr", "b.week", "yes");
        }
        // boost every day between start/end day off
        if (newdata[2] == 2) {
            OvmsConfig.Set("usr", "b.week", "no");
        }
        // booster scheduled time + boost scheduled on
        if (newdata[3]> 0) {
            PubSub.unsubscribe(state.scheduled_boost);
            OvmsConfig.Set("usr", "b.scheduled", newdata[3]);
            var ClockEvent = 'clock.' + newdata[3];
            state.scheduled_boost = PubSub.subscribe(ClockEvent,scheduled_boost);
        }
        // booster start day + boost scheduled on + boost weekly on
        if (newdata[4] > -1) {
            PubSub.unsubscribe(state.start_day);
            // add one Day for execute on last Day, Day 7 not possible -> set Day 0 for Sunday
            if (newdata[4] > 6) {
                var scheduled_start = 0
            } else {
                var scheduled_start = newdata[4]
            };
            OvmsConfig.Set("usr", "b.day_start", scheduled_start);
            var DayStart = 'clock.day' + scheduled_start;
            state.start_day = PubSub.subscribe(DayStart,start_day);
        }
        // booster stop day
        if (newdata[5] > -1) {
            PubSub.unsubscribe(state.end_day);
            // add one Day for execute on last Day, Day 7 not possible -> set Day 0 for Sunday
            if (newdata[5] <= 5) {
                var scheduled_end = (Number(newdata[5]) +1)
            } else {
                var scheduled_end = 0
            };
            OvmsConfig.Set("usr", "b.day_end", scheduled_end);
            var DayEnd = 'clock.day' + scheduled_end;
            state.end_day = PubSub.subscribe(DayEnd,end_day);
        }
        // double booster time
        if (newdata[6] == 1) {
            OvmsConfig.Set("usr", "b.scheduled_2", "yes");
        }
        // single booster time
        if (newdata[6] == 0) {
            OvmsConfig.Set("usr", "b.scheduled_2", "no");
        }
        OvmsConfig.Set("usr", "b.data", "0,0,0,0,-1,-1,-1");
    }

    if((!state.scheduled_boost)&&(OvmsConfig.Get("usr","b.activated") == "yes")){
        state.scheduled_boost = PubSub.subscribe('clock.0001',veh_on);
        PubSub.unsubscribe(state.scheduled_boost);
        state.scheduled_boost_2 = PubSub.subscribe('clock.0001',veh_on);
        PubSub.unsubscribe(state.scheduled_boost_2);
        var ClockEvent = 'clock.' + OvmsConfig.Get("usr","b.scheduled");
        state.scheduled_boost = PubSub.subscribe(ClockEvent,scheduled_boost);
    }
    if((!state.start_day)&&(OvmsConfig.Get("usr","b.week") == "yes")){
        state.start_day = PubSub.subscribe('clock.0001',veh_on);
        PubSub.unsubscribe(state.start_day);
        state.end_day = PubSub.subscribe('clock.0001',veh_on);
        PubSub.unsubscribe(state.end_day);
        var DayStart = 'clock.day' + OvmsConfig.Get("usr","b.day_start");
        var DayEnd = 'clock.day' + OvmsConfig.Get("usr","b.day_end");
        state.start_day = PubSub.subscribe(DayStart,start_day);
        state.end_day = PubSub.subscribe(DayEnd,end_day);
    }
}

PubSub.subscribe(scheduled_ticker,booster_data);
