import { lusitana } from '@/app/ui/fonts';
import React from 'react';
import { BarChart } from '@/app/ui/dashboard/bar-chart';
import NoSsr from '@/app/ui/no-ssr';

export default function Home() {

  return (
    <main>
      <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
        Dashboard
      </h1>
      <NoSsr>
        <BarChart/>
      </NoSsr>
    </main>
  );
}
