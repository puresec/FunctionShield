const fs = require("fs");
const child_process = require("child_process");
const FunctionShield = require('@puresec/function-shield');
FunctionShield.configure(
    {
        policy: {
            read_write_tmp: "alert",
            create_child_process: "alert",
            outbound_connectivity: "alert",
            read_handler: "alert"
        },
        disable_analytics: false,
        token: process.env.FUNCTION_SHIELD_TOKEN
    });

module.exports.hello = (event, context, callback) => {
    fs.writeFileSync("/tmp/node-alert", "data");
    child_process.execSync("touch /tmp/child-alert");
    child_process.execSync("curl google.com");
    fs.readFileSync('/var/task/handler.js');
    child_process.execSync("cat /var/task/handler.js");
    callback(null, "done");
};
