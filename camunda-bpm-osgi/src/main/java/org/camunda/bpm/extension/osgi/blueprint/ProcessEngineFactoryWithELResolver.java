package org.camunda.bpm.extension.osgi.blueprint;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.el.VariableScopeElResolver;
import org.camunda.bpm.engine.impl.javax.el.ArrayELResolver;
import org.camunda.bpm.engine.impl.javax.el.CompositeELResolver;
import org.camunda.bpm.engine.impl.javax.el.ELResolver;
import org.camunda.bpm.engine.impl.javax.el.ListELResolver;
import org.camunda.bpm.engine.impl.javax.el.MapELResolver;
import org.camunda.bpm.engine.impl.scripting.BeansResolverFactory;
import org.camunda.bpm.engine.impl.scripting.ResolverFactory;
import org.camunda.bpm.engine.impl.scripting.ScriptBindingsFactory;
import org.camunda.bpm.engine.impl.scripting.VariableScopeResolverFactory;
import org.camunda.bpm.extension.osgi.OsgiScriptingEngines;


public class ProcessEngineFactoryWithELResolver extends ProcessEngineFactory {

    private BlueprintELResolver blueprintELResolver;

    @Override
    public void init() {
      ProcessEngineConfigurationImpl configImpl = (ProcessEngineConfigurationImpl) getProcessEngineConfiguration();
      configImpl.setExpressionManager(new BlueprintExpressionManager());
      
      List<ResolverFactory> resolverFactories = configImpl.getResolverFactories();
      if (resolverFactories == null) {
        resolverFactories = new ArrayList<ResolverFactory>();
        resolverFactories.add(new VariableScopeResolverFactory());
        resolverFactories.add(new BeansResolverFactory());
      }
      
      configImpl.setScriptingEngines(new OsgiScriptingEngines(new ScriptBindingsFactory(resolverFactories)));
      super.init();
    }

    public class BlueprintExpressionManager extends ExpressionManager {
      @Override
      protected ELResolver createElResolver(VariableScope variableScope) {
        CompositeELResolver compositeElResolver = new CompositeELResolver();
        compositeElResolver.add(new VariableScopeElResolver(variableScope));
        compositeElResolver.add(blueprintELResolver);
        compositeElResolver.add(new ArrayELResolver());
        compositeElResolver.add(new ListELResolver());
        compositeElResolver.add(new MapELResolver());
        return compositeElResolver;
      }
    }
    
    public void setBlueprintELResolver(BlueprintELResolver blueprintELResolver) {
      this.blueprintELResolver = blueprintELResolver;
    }
}
