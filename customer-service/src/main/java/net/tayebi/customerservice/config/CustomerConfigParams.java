package net.tayebi.customerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ConfigurationProperties(prefix = "customer.params")

public record CustomerConfigParams(int x, int y) {

    }

