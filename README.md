# OSGi integration for camunda BPM platform

camunda BPM community extension providing support for camunda BPM platform inside OSGi containers

```
Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: camunda BPM Platform OSGi
Bundle-SymbolicName: org.camunda.bpm.extension.osgi
Bundle-Version: 
Export-Package: [...]
Import-Package: [...]
```

## Get started

### Part 1 starting the camunda BPM OSGi bundle

Before you start you have to install all the required bundles into your OSGi runtime.
To see a list of the required bundles you can take a look at the Apache Karaf feature.xml.
It contains a list of the required dependencies and a list containing all the optional dependencies, too.

After you've done that you can drop the camunda-bpm-osgi bundle into the runtime.
It should then move to the resolved state and you could start it.

If you prefer to use Apache Karaf as your runtime you can use the feature.xml directly. The guide can be found [here](https://github.com/camunda/camunda-bpm-platform-osgi/blob/master/camunda-engine-karaf-feature/README.md).

### Part 2 creating a process engine

After you successfully deployed the camunda BPM OSGi bundle your next step is to create a ProcessEngine.

#### Using the ProcessEngineFactory

To help a little bit with the creating of a process engine you can use the `ProcessEngineFactory` class. You'll have to pass a `ProcessEngineConfiguration` object and the current bundle to it by calling the setBundle() and setProcessEngineConfiguration() methods. Finally you'll have to call init(). After that you may call getObject() to get a reference to the process engine.
Please be aware that the order is mandatory or else getObject() will return null.

Please note also, that the process engine won't be exported automatically. If you want to share it, you can do that by yourself.

#### Using the camunda BPM Blueprint wrapper (deprecated)

There is already a project with a pre-filled Blueprint context.xml. Basically you'll only have to edit the Datasource properties or you use the pre-defined in memory H2 database.

This approach uses the deprecated `ConfigurationFactory` right now, so please be careful. To see the reason why the `ConfigurationFactory` is deprecated see [here](https://groups.google.com/forum/#!topic/camunda-bpm-dev/toZEYMzUJpQ)

If your Blueprint implementation supports non-void setters you can replace the `ConfigurationFactory` by directly configuring a `StandaloneProcessEngineConfiguration`. 

#### Old school

If you wanna stay old school and use core OSGi you can do that, too.
Import the package `org.camunda.bpm.engine` and `org.camunda.bpm.engine.impl.cfg` and instantiate your own `StandaloneProcessEngineConfiguration`.

### Part 3 Deploying process definitions

After you created a `ProcessEngine` you can start to deploy process definitions.
The following steps only work when you exported a `ProcessEngine` as OSGi service.

#### Inside a bundle

When you deploy a bundle containing a process definition the process can be automatically added to the ProcessEngine.
For the process definition to be found, you'll have to do one of the following things:
- place it in the OSGI-INF/processes/ directory
- set the "Process-Definitions" header in the MANIFEST.MF and let it point to a file or directory

If you reference any `JavaDelegate`s or `ActivityBehavior`s from within your process defniition please take a look at Part 4

#### BPMN-XML file

If your OSGi runtime supports Apache Felix Fileinstall you can drop a single process definition in the directory watched by Fileinstall. It will be parsed and automatically transformed into an OSGi bundle.

### Part 4 referencing inside processes

Right now the `BlueprintELResolver` is the only cares for `JavaDelegates`. You'll have to use the `BlueprintELResolver` as ELResolver and register it to listen for `JavaDelegates`.
If you use the camunda BPM Blueprint wrapper this will be done for you automatically.

The only other ways is to use the setBeans() method on the `ProcessEngineConfiguration`.

## Resources

* [Issue Tracker](https://github.com/camunda/camunda-bpm-platform-osgi/issues)
* [Contributing](https://github.com/camunda/camunda-bpm-platform-osgi/blob/master/CONTRIBUTING.md)


## Roadmap

_a short list of things that yet need to be done (until we organize it elsewhere ;) )_

**todo**
- adapt Process Application API for OSGi
- camunda webapp WAB (cockpit, tasklist, admin)
- create example for configuring engine using PAX-CDI

**done**
- QA, integration tests (resolve engine-bundle)
- example for configuring engine using Blueprint


## Maintainers:

* [@rbraeunlich ](https://github.com/rbraeunlich)

## License

Apache License, Version 2.0
