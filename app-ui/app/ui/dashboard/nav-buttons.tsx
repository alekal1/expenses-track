'use client'

import { ArrowDownOnSquareIcon, ArrowUpOnSquareIcon } from '@heroicons/react/24/outline';

export default function NavButtons() {

  function exportData() {
    fetch('/v1/management', {
      method: 'GET'
    })
      .then((response) => response.blob())
      .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const link: HTMLAnchorElement = document.createElement('a');
        link.href = url;
        link.download = 'ce_data.json'
        link.click();
        URL.revokeObjectURL(url);
      })
  }

  return (
    <div className={'flex justify-between'}>
      <button
        onClick={() => exportData()}
        className={'flex h-[48px] grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 text-sm font-medium hover:bg-sky-100 hover:text-blue-600 md:flex-none md:justify-start md:p-2 md:px-3'}
      >
        <ArrowDownOnSquareIcon className="w-6"/>
        <p className="hidden md:block">Export</p>
      </button>
      <button
        className={'flex h-[48px] grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 text-sm font-medium hover:bg-sky-100 hover:text-blue-600 md:flex-none md:justify-start md:p-2 md:px-3'}
      >
        <ArrowUpOnSquareIcon className="w-6"/>
        <p className="hidden md:block">Import</p>
      </button>
    </div>
  )
}