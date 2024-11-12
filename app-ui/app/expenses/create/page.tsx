'use client'

import Breadcrumbs from '@/app/ui/breadcrumbs';
import { useEffect, useState } from 'react';
import { CeResponse } from '@/app/model/ceResponse';
import { Tag } from '@/app/model/tag';
import { CreateForm } from '@/app/ui/expenses/createForm';


export default function Page() {
  const [tags, setTags] = useState<Tag[]>([{name: 'test'}, {name: 'test v2'}]);

  useEffect(() => {
    async function fetchTags() {
      const res = await fetch('/v1/tag', {
        method: 'GET'
      })
      const ceResponse: CeResponse = await res.json()

      setTags(ceResponse.data as Tag[])
    }

    fetchTags()
  }, [])

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
      <CreateForm tags={tags}/>
    </main>
  )
}