package com.levtea.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GatewayFlowRulesController {
  @GetMapping("/gw/flow/rules")
  public Set<GatewayFlowRule> getGatewayFlowRules() {
    return GatewayRuleManager.getRules();
  }

  @GetMapping("/gw/flow/groups")
  public Set<ApiDefinition> getApiGroupRules() {
    return GatewayApiDefinitionManager.getApiDefinitions();
  }
}