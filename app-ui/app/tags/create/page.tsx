import Breadcrumbs from '@/app/ui/breadcrumbs';
import { CreateForm } from '@/app/ui/tags/createForm';

export default function Page() {
  return (
    <main>
      <Breadcrumbs breadcrumbs={[
        {
          label: 'Tags', href: '/tags'
        },
        {
          label: 'Create tag',
          href: '/tags/create',
          active: true
        }
      ]}/>
      <CreateForm/>
    </main>
  )
}