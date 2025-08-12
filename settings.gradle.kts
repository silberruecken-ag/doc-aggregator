rootProject.name = "doc-aggregator"

include("doc-aggregator-service")
include("doc-aggregator-autoconfigure")

includeBuild("../privat/spring-template-editor") // TODO: Only temporary solution
