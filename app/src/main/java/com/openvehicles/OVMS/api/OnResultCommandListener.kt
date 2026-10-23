package com.openvehicles.OVMS.api

interface OnResultCommandListener {

    /**
     * MP command execution result callback
     *
     * Depending on the command, the output may be comma separated values (without value encoding),
     * raw text, or using some specific encoding. Many/most MP commands follow the simple CSV
     * scheme, so the callback provides a pre-parsed result array for convenience.
     *
     * @param commandCode   -- numerical MP command code
     * @param returnCode    -- command return code (0=ok, 1=failed, 2=unsupported, 3=unimplemented)
     * @param returnData    -- raw command data response / output
     * @param result        -- pre-parsed server response, split at ',', with
     *                          [0] = commandCode, [1] = returnCode, [2…] = returnData fields
     */
    fun onResultCommand(
        commandCode: Int,
        returnCode: Int,
        returnData: String,
        result: Array<String>
    )

}
