'use client'

import { lusitana } from '@/app/ui/fonts';
import { Tag } from '@/app/model/tag';
import { CeResponse } from '@/app/model/response/ceResponse';
import { useEffect, useState } from 'react';
import { CreateTag } from '@/app/ui/tags/buttons';

export default function Page() {
  const [availableTags, setAvailableTags] = useState<Tag[]>([]);

  useEffect(() => {
    async function fetchAvailableTags() {
      const res = await fetch('/v1/tag', {
        method: 'GET'
      })
      const ceResponse: CeResponse = await res.json()

      setAvailableTags(ceResponse.data as Tag[])
    }

    fetchAvailableTags()
  }, [])

  return (
    <div>
      <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
        Tags
      </h1>
      <div className={'py-8 px-4 mx-auto max-w-screen-xl text-left lg:py-16'}>
        <div className={'mt-4 flex items-center justify-between gap-2 md:mt-8'}>
          <h1
            className={`${lusitana.className} mb-4 text-4xl tracking-tight leading-none text-gray-900 md:text-5xl lg:text-6xl`}>
            Available tags:
          </h1>
          <CreateTag/>
        </div>
        {availableTags.map((avTag) => {
          return (
            <span
              key={avTag.name}
              className="bg-gray-100 text-gray-800 text-sm font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">
            {avTag.name}
          </span>
          )
        })}
      </div>
    </div>
  )
}