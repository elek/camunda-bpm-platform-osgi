# camunda Engine feature
This project contains a feature.xml to install the camunda-engine and camunda-engine-osgi project into your Karaf installation.

Check out this project and run mvm install on it. Then start Karaf and type the following:
feature:repo-add mvn:org.camunda.bpm.extension.osgi/camunda-bpm-karaf-feature/{version}/xml/features

Now you can install two versions:
feature:install camunda-bpm-karaf-feature-minimal
or
feature:install camunda-bpm-karaf-feature-full

The full version contains bundles for all the optional imports.
