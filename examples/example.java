package com.serverless;

import java.util.Map;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.json.JSONObject;
import io.puresec.FunctionShield;

public class Handler implements RequestHandler <Map <String, Object> , String > {
    static {
        FunctionShield.configure(new JSONObject()
            .put("policy", new JSONObject()
                .put("read_write_tmp", "alert")
                .put("create_child_process", "alert")
                .put("outbound_connectivity", "alert")
                .put("read_handler", "alert"))
            .put("disable_analytics", false)
            .put("token", System.getenv("FUNCTION_SHIELD_TOKEN")));
    }

    @Override
    public String handleRequest(Map <String, Object> input, Context context) {
        try {
            new FileOutputStream(new File("/tmp/java-alert")).close();
            FileInputStream fin= new FileInputStream("/var/task/com/serverless/Handler.class");
            int i=fin.read();
            fin.close();
            URL url = new URL("http://www.microsoft.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(con.getInputStream());
            String res = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
            System.out.println(res);

            Runtime.getRuntime().exec("curl -o /tmp/eicar http://www.eicar.org/download/eicar.com.txt").waitFor();
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
        return "success";
    }
    public static void main(String[] args){

    }

}
