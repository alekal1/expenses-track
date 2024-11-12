'use client'

import { CeResponse } from '@/app/model/response/ceResponse';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import { SearchExpensesResponse } from '@/app/model/response/searchExpensesResponse';

export default function ExpensesTable() {
  const searchParams = useSearchParams();
  const query = searchParams?.get('query') ?? '';
  // const currentPage = Number(searchParams?.get('page')) || 0;

  const [searchResponse, setSearchResponse] = useState<SearchExpensesResponse>();

  useEffect(() => {
    async function fetchExpenses() {
      const url = query ? `/v1/expense?tagName=${query}` : `/v1/expense`;
      const res = await fetch(url, {
        method: 'GET'
      })
      const ceResponse: CeResponse = await res.json();

      if (200 === ceResponse.httpStatus) {
        setSearchResponse(ceResponse.data as SearchExpensesResponse)
      }
    }

    fetchExpenses()
  }, [query]);


  return (
    <main>
      <div className={"mt-6 flow-root"}>
        <div className={'inline-block min-w-full align-middle'}>
          <div className={'rounded-lg bg-gray-50 p-2 md:pt-0'}>
            <div className={'md:hidden'}>
              {searchResponse?.content.map((expense) => (
                <div
                  key={expense.id}
                  className={'mb-2 w-full rounded-md bg-white p-4'}
                >
                  <div className={'flex items-center justify-between border-b pb-4'}>
                    <div>
                      <div className={'mb-2 flex items-center'}>
                        <p>{expense.id}</p>
                      </div>
                      <p className={'text-sm text-gray-500'}>{expense.tagName}</p>
                    </div>
                  </div>
                  <div className={'flex w-full items-center justify-between pt-4'}>
                    <div>
                      <p className={'text-xl font-medium'}>
                        {expense.amount}
                      </p>
                      <p>
                        {expense.description}
                      </p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
            <table className={'hidden min-w-full text-gray-900 md:table'}>
              <thead className={'rounded-lg text-left text-sm font-normal'}>
              <tr>
                <th scope="col" className="px-4 py-5 font-medium sm:pl-6">
                  Id
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Tag
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Amount
                </th>
                <th scope="col" className="px-3 py-5 font-medium">
                  Description
                </th>
              </tr>
              </thead>
              <tbody className={'bg-white'}>
              {
                searchResponse?.content.map((expense) => (
                  <tr
                    key={expense.id}
                    className={'w-full border-b py-3 text-sm last-of-type:border-none [&:first-child>td:first-child]:rounded-tl-lg [&:first-child>td:last-child]:rounded-tr-lg [&:last-child>td:first-child]:rounded-bl-lg [&:last-child>td:last-child]:rounded-br-lg'}
                  >
                    <td className={'whitespace-nowrap py-3 pl-6 pr-3'}>
                      <div className={'flex items-center gap-3'}>
                        <p>{expense.id}</p>
                      </div>
                    </td>
                    <td className={'whitespace-nowrap px-3 py-3'}>
                      {expense.tagName}
                    </td>
                    <td className={'whitespace-nowrap px-3 py-3'}>
                      {expense.amount}
                    </td>
                    <td className="whitespace-nowrap px-3 py-3">
                      {expense.description}
                    </td>
                  </tr>
                ))
              }
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </main>
  )
}