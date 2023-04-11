<#-- @ftlvariable name="game" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h1 class="text-4xl text-center mb-4 text-[#cdc9cb]">
            ${game.name}
        </h1>
        <hr>
        <div class="mb-4 text-[#cdc9cb] px-4 py-5">
            <p>
                ${game.description}
            </p>
            <div class="flex flex-col items-center ">
                <a class="bg-[#cdc9cb] m-3 hover:bg-[#ce5936] text-[#a58c4a] font-bold py-2 px-4 border border-[#ce5936] rounded" href="/game/${game.id}/new">
                  Track Game
                </a>
            </div>
        </div>
    </div>
</@layout.header>