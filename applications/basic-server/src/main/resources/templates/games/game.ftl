<#-- @ftlvariable name="gameNewsData" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <div>
        <h1 class="my-4 text-5xl m-5 text-center leading-none tracking-tight md:text-5xl lg:text-6xl text-[#a58c4a] game-name" style="font-family: 'Ultra', serif;">${gameNewsData.name}</h1>
        <hr>
        <div class="mb-4 text-[#cdc9cb] px-4 py-5 game-description">
            <p>
                ${gameNewsData.description}
            </p>
        </div>
        <hr />

        <h3 class="text-center text-[#cdc9cb] font-bold text-2xl p-4">
            Recent News
        </h3>
        <div class="flex flex-col flex items-center justify-center">
            <div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
                <#list gameNewsData.news as n>
                    <div class="flex items-center justify-center">
                        <div class="inline-block min-w-[50rem] rounded-lg mx-[5rem] py-2 m-3">
                            <a href=${n.url} target="_blank">
                                <div class="border-r border-b border-l border-gray-400 lg:border-l-0 lg:border-t lg:border-gray-400 bg-white rounded-b lg:rounded-b-none lg:rounded-r p-4 flex flex-col justify-between leading-normal">
                                    <div class="mb-8">
                                        <div class="text-gray-900 font-bold text-xl mb-2 news-title">${n.title}</div>
                                        <p class="text-gray-700 text-base news-desc">${n.description}</p>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</@layout.header>