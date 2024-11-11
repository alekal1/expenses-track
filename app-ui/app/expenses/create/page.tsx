import Breadcrumbs from '@/app/ui/breadcrumbs';
import { env } from 'process';


export default async function Page() {
  console.log(process.env.NODE_ENV)

  return (
    <main>
      <Breadcrumbs breadcrumbs={[
        {
          label: 'Expenses', href: '/expenses'
        },
        {
          label: 'Create expense',
          href: '/expenses/create',
          active: true
        }
      ]}/>
    </main>
  )
}