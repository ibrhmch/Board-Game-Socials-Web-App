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
        </div>
    </div>
</@layout.header>