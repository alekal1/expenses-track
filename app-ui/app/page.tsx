import { lusitana } from '@/app/ui/fonts';
import React from 'react';

export default function Home() {
  return (
    <main>
      <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
        Dashboard
      </h1>
    </main>
  );
}
