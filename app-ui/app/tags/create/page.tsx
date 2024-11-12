import Breadcrumbs from '@/app/ui/breadcrumbs';

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
    </main>
  )
}