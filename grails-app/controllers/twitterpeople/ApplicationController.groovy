package twitterpeople

import grails.core.GrailsApplication
import grails.util.Environment
import grails.plugins.*

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {

        params.kk = 'una kk'
        println params

        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
