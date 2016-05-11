package twitterpeople

import grails.rest.Resource

import javax.validation.constraints.Size

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

    @Size(min = 8)
    String description
    int followersCount
    int friendsCount
    String location

    Date dateCreated
}
