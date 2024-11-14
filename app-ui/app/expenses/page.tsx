import { lusitana } from '@/app/ui/fonts';
import Search from '@/app/ui/search';
import { CreateExpense } from '@/app/ui/expenses/buttons';
import { Suspense } from 'react';
import ExpensesTable from '@/app/ui/expenses/table';
import Pagination from '@/app/ui/expenses/pagination';


export default function Page() {
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
      <Suspense fallback={<h1>Loading...</h1>}>
        <ExpensesTable/>
        <Pagination/>
      </Suspense>
    </div>
  )
}