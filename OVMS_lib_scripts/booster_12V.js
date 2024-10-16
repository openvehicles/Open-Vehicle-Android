/*
    Installation:
     - Save as /store/scripts/lib/booster_12v.js
     - Add to /store/scripts/ovmsmain.js: booster_12v = require("lib/booster_12v");
     - Issue "script reload"
*/


// create config usr value
var init_usrcfg = OvmsConfig.Get("usr","12v.init", "no");

if(init_usrcfg == "no"){
    OvmsConfig.Set("usr", "12v.init", "yes");      // init usr config
    OvmsConfig.Set("usr", "12v.time_1", "1900");   // schedule time 1 format 1400 for 14:00 / 2 p.m. o'clock 
    OvmsConfig.Set("usr", "12v.time_2", "0500");   // schedule time 2 format 1400 for 14:00 / 2 p.m. o'clock 
    OvmsConfig.Set("usr", "12v.charging", "yes");  // activate 12V charging events
}

var state = {
  time_1: false,       // time 1 subscription
  time_2: false,       // time 2 subscription
  booster_2: false,    // 2x booster subscription
};

// delete old events
PubSub.unsubscribe(charge_12v_check);

// get value
var time_on_1 = 'clock.' + OvmsConfig.Get("usr","12v.time_1", "1900"); 
var time_on_2 = 'clock.' + OvmsConfig.Get("usr","12v.time_2", "0500");
var charging_12v = OvmsConfig.Get("usr", "12v.charging", "yes");

function veh_on() {
  return OvmsMetrics.Value("v.e.on");
}
function charging() {
  return OvmsMetrics.Value("v.c.charging");
}

function veh_hvac() {
  return OvmsMetrics.Value("v.e.hvac");
}

function charge_12v_check() {
  
  var voltage_12v = OvmsMetrics.Value("v.b.12v.voltage");
  var alert_12v = OvmsConfig.Get("vehicle","12v.alert","12.2");

  if(!veh_on() && !charging())  {
      if (voltage_12v <= alert_12v ) {
        OvmsVehicle.ClimateControl("on");
        state.booster_2 = PubSub.subscribe('ticker.60',scheduled_boost_2);
      }
  }
}

// scheduled booster time add a second booster time for 10 min. booster
function charge_12v_boost_2() {
  if(!veh_on() && !charging() && !veh_hvac()) {
      OvmsVehicle.ClimateControl("on");
      PubSub.unsubscribe(state.booster_2);
  }
  if(veh_on()) {
      PubSub.unsubscribe(state.booster_2);
  }
}

// event creating
if((!state.time_1)&&(charging_12v == "yes")){
  state.time_1 = PubSub.subscribe('clock.0001',veh_on);
  PubSub.unsubscribe(state.time_1);
  state.time_2 = PubSub.subscribe('clock.0001',veh_on);
  PubSub.unsubscribe(state.time_2);
  state.booster_2 = PubSub.subscribe('clock.0001',veh_on);
  PubSub.unsubscribe(state.booster_2);
  state.time_1 = PubSub.subscribe(time_on_1,charge_12v_check);
  state.time_2 = PubSub.subscribe(time_on_2,charge_12v_check);
}
