<#-- @ftlvariable name="session" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h1>TODO Show session INformation</h1>
        <h1 class="text-4xl text-center mb-4 text-[#cdc9cb]">
            Session Name: ${session.sessionName}
        </h1>
        <hr>
        <div class="mb-4 text-[#cdc9cb] px-4 py-5">
            <p>
                Game: ${session.game}
            </p>
            <p>
                Number of player: ${session.players}
            </p>
            <p>
                <h1>TODO Allow players to update wins and losses</h1>
                Player Name - 1 <br />
                wins - 0  (+ | -)<br />
                loss - 2 (+ | -)<br />
            <p>
        </div>
    </div>
</@layout.header>