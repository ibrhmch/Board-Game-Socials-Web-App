<#-- @ftlvariable name="sessions" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h1 class="text-4xl text-center mb-4 text-[#cdc9cb]">
            Current Sessions
        </h1>
        <hr>
        <div class="mb-4 text-[#cdc9cb] px-4 py-5">
            <div class="flex flex-col">
              <div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
                <div class="inline-block min-w-full py-2 sm:px-6 lg:px-8">
                  <div class="overflow-hidden">
                    <table class="min-w-full text-left text-sm font-light">
                      <thead class="border-b font-medium dark:border-neutral-500">
                        <tr>
                          <th scope="col" class="px-6 py-4">#</th>
                          <th scope="col" class="px-6 py-4">Session Name</th>
                          <th scope="col" class="px-6 py-4">Game</th>
                          <th scope="col" class="px-6 py-4">Players</th>
                          <th scope="col" class="px-6 py-4">Actions</th>
                        </tr>
                      </thead>
                      <tbody>
                      <#list sessions?reverse as session>
                        <a href="sessions/{session.id}">
                            <tr class="border-b dark:border-neutral-500">
                              <td class="whitespace-nowrap px-6 py-4 font-medium">${session.id}</td>
                              <td class="whitespace-nowrap px-6 py-4"><a href="sessions/${session.id}">${session.sessionName}</a></td>
                              <td class="whitespace-nowrap px-6 py-4">${session.game}</td>
                              <td class="whitespace-nowrap px-6 py-4">${session.players}</td>
                              <td class="whitespace-nowrap px-6 py-4">
                                <i class="fa text-2xl fa-pencil-square" aria-hidden="true"></i>
                                <i class="fa text-2xl fa-trash" aria-hidden="true"></i>
                              </td>
                            </tr>
                        </a>
                      </#list>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
        </div>
    </div>
</@layout.header>