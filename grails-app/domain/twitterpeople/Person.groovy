package twitterpeople

import grails.rest.Resource

@Resource(uri='/people', formats=['json','hal'], readOnly=true)
class Person {

    static constraints = {
        description nullable:true
        location nullable:true
    }

    static mapping = {
        id generator: 'assigned', type:'string'
    }

    String id   // usaremos el name en lugar del id de twitter
    String description
    int followersCount
    int friendsCount
    String location

    static hasMany = [ someFriends : Person ]
}
