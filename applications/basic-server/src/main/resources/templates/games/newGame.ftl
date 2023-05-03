<#-- @ftlvariable name="game" -->
<#import "../_layout.ftl" as layout />
<@layout.header>
    <form action="/games" method="POST" class="w-full p-5">
        <h1 class="my-4 text-5xl m-5 text-center leading-none tracking-tight md:text-5xl lg:text-6xl text-[#a58c4a]" style="font-family: 'Ultra', serif;">Add Game</h1>
        <div class="mb-4 w-full flex justify-center">

            <div class="flex flex-col w-[65%]">
                <label for="message" class="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Name</label>
                <input class="shadow mb-3 appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="name" name="name" type="text" placeholder="Name">

                <label for="message" class="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Description</label>
                <textarea id="message" rows="4" class="block mb-2 p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600
                 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" name="description" placeholder="Description"></textarea>

                <div class="flex">
                    <button class="inline-block submit-game my-2 bg-[#cdc9cb] hover:bg-[#ce5936] text-[#a58c4a] font-bold py-2 px-4 border border-[#ce5936] rounded" type="submit">Add Game</button>
                </div>
            </div>

        </div>
    </form>
</@layout.header>