/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * OVMS adaptation by Michael Balzer (5 Nov 2015)
 *
 */

package com.openvehicles.OVMS.receiver;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "gcmRegIntentService";
    private static final String[] TOPICS = {"global"};

	public static final String GCM_TOKEN = "gcmToken";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

		// Android bug?
    	if (intent == null) {
			Log.e(TAG, "invalid intent -- abort");
			return;
		}

		String vehicleId = intent.getStringExtra("ovmsVehicleId");
		String gcmSenderId = intent.getStringExtra("ovmsGcmSenderId");
		Log.i(TAG, "doing registration for vehicleId=" + vehicleId
				+ ", gcmSenderId=" + gcmSenderId);

		if (vehicleId == null || gcmSenderId == null) {
			Log.e(TAG, "invalid intent data -- abort");
			return;
		}

		SharedPreferences sharedPreferences = getSharedPreferences("GCM." + vehicleId, 0);

		try {
			// [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(gcmSenderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]

			// store token in vehicle prefs:
			Log.i(TAG, "got registration token: " + token);
			sharedPreferences.edit().putString(GCM_TOKEN, token).apply();

            // Subscribe to topic channels
            subscribeTopics(token);

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
			sharedPreferences.edit().putString(GCM_TOKEN, "").apply();
        }

        // Notify MainActivity that registration has completed:
        Intent notify = new Intent(REGISTRATION_COMPLETE);
		notify.putExtra("ovmsVehicleId", vehicleId);
		LocalBroadcastManager.getInstance(this).sendBroadcast(notify);
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}
