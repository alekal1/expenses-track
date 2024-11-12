import { lusitana } from '@/app/ui/fonts';
import Search from '@/app/ui/search';
import { CreateExpense } from '@/app/ui/expenses/buttons';
import { Suspense } from 'react';

// type ExpensePageProps = {
//   searchParams?: Promise<{
//     query?:string;
//     page?: string;
//   }>
// }
//
// props: Readonly<ExpensePageProps>

export default async function Page() {
  // const searchParams = await props.searchParams;
  // const query = searchParams?.query ?? '';
  // const currentPage = Number(searchParams?.page) || 0;
  //
  //
  // console.log(searchParams, query, currentPage);

  return (
    <div className={"w-full"}>
      <div className="flex w-full items-center justify-between">
        <h1 className={`${lusitana.className} text-2xl`}>Expenses</h1>
      </div>
      <div className={"mt-4 flex items-center justify-between gap-2 md:mt-8"}>
        <Suspense fallback={<div>Loading...</div>}>
          <Search placeHolder={"Search expenses by tag..."}/>
        </Suspense>
        <CreateExpense/>
      </div>
    </div>
  )
}