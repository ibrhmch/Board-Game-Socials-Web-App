<#-- @ftlvariable name="sessionData" -->
<#import "../_layout.ftl" as layout />


<@layout.header>
    <#list sessionData as session>
        ${session.sessionId}
        ${session.playerName}
        ${session.gameName}
        ${session.wins}
        ${session.losses}
    </#list>
</@layout.header>