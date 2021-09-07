package com.tinufarid.testgwapi;

import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import de.svenjacobs.loremipsum.LoremIpsum;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@RestController
public class SampleEndpoint {

    @GetMapping("/")
    public String greeting(){

        return "Hey, Stop looking here";
    }

    @GetMapping("/trending/bullish")
    @ResponseBody


    public JSONArray getDataFromDB() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con= DriverManager.getConnection(
                "jdbc:mysql://ms.itversity.com:3306/retail_db?" +
                        "useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                        "&useLegacyDatetimeCode=false" +
                        "&serverTimezone=UTC","retail_user","itversity");

        Statement stmt=con.createStatement();
        ResultSet rs= ((Statement) stmt).executeQuery("select * from retail_export.tdstk_trending_stock\n" +
                "where sentiment = 'Bullish'\n" +
                "order by title, latest_mentioned_date desc, number_of_mentions desc;");




        String message;
        //JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();


        while(rs.next()) {
            //System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            JSONObject json = new JSONObject();

            json.put("symbol",rs.getString(1));
            json.put("title",rs.getString(2));
            json.put("sentiment",rs.getString(3));
            json.put("number_of_mentions",rs.getString(4));
            json.put("last_mentioned_date",rs.getString(5));
            array.add(json);

        }

        rs.close();

        con.close();


        return array;

    }




}
