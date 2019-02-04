import os
import function_shield
import subprocess

function_shield.configure({
    "policy": {
        "read_write_tmp": "alert",
        "create_child_process": "alert",
        "outbound_connectivity": "alert",
        "read_handler": "block"
    },
    "disable_analytics": False,
    "token": os.environ['FUNCTION_SHIELD_TOKEN']
})


def hello(event, context):
    print("started")
    with open('/tmp/python-alert', 'w'):
        pass
    with open('/var/task/handler.py', 'r'):
        pass

    subprocess.Popen('touch /tmp/child-alert', shell=True)
    os.system('curl google.com')
    os.system('curl aws.amazonaws.com')
    os.system('cat /var/task/handler.py')
    return True
