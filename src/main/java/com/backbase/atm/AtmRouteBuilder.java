package com.backbase.atm;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.rest.RestBindingMode;

/**
 * Created by admin on 8/31/15.
 */
public class AtmRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                //output in pretty print
                .dataFormatProperty("prettyPrint", "true");

        // here are all my possible routes - all on top of /atms
        rest("/atms").description("ATM rest service")
                .consumes("application/json").produces("application/json")

                .get("/").description("Find all atms").outType(Service.class)
                .to("bean:atmService?method=getATMs()")

                .get("/city/{city}").description("Find user by id").outType(Service.class)
                    .to("bean:atmService?method=getATMsByCity(${header.city})")

                .get("/type/{type}").description("Find user by id").outType(Service.class)
                    .to("bean:atmService?method=getATMsByType(${header.type})");
    }
}