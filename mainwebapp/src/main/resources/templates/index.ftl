{
    "user": {
        "name": "${name}",
        "age": ${age},
        "isAdult": ${(age >= 18)?string("true", "false")},
        "isAdult2": <#if (age >= 18)> "true" <#else> "false" </#if>,
        "hobbies": [
        <#list hobbies as hobby>
            "${hobby}"<#if hobby_has_next>,</#if>
        </#list>
        ]
    },
    "message": "${message}",
    "accountStatus": "${status!"unknown"}",
    "features": {
        "freeTrial": ${features.freeTrial?c},
        "premiumAccess": ${features.premiumAccess?c}
    },
    "meta": {
        "requestUrl": "${requestUri}",
        "createdAt": "${.now?string('yyyy-MM-dd HH:mm:ss')}",
        "randomId": "${randomId?string}"
    },
    "conditionalContent":
    <#if age < 18>
        "Restricted"
    <#else>
        "Full Access"
    </#if>,
    "nestedExample": [
    <#list users as user>
        {
            "id": ${user.id},
            "name": "${user.name}",
            "role": "${user.role}"
        }<#if user_has_next>,</#if>
    </#list>
    ]
}
