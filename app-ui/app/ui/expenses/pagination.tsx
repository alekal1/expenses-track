'use client'

import { clsx } from 'clsx';
import { ArrowLeftIcon, ArrowRightIcon } from '@heroicons/react/24/outline';
import Link from 'next/link';
import { usePathname, useSearchParams } from 'next/navigation';
import { generatePagination } from '@/app/lib/utils';

export default function Pagination() {
  const pathname = usePathname()
  const searchParams = useSearchParams();
  const currentPage = Number(searchParams.get('page')) || 1;
  const totalPages = Number(searchParams?.get('totalPages')) || 0;

  const createPageURL = (pageNumber: number | string) => {
    const params = new URLSearchParams(searchParams);
    params.set('page', pageNumber.toString());
    return `${pathname}?${params.toString()}`
  }

  const allPages = generatePagination(currentPage, totalPages);

  return (
    <div className={'inline-flex mt-10'}>
      <PaginationArrow
        href={createPageURL(currentPage - 1)}
        direction={'left'}
        isDisabled={currentPage <= 1}/>

      <div className={'flex -space-x-px'}>
        {allPages.map((page, index) => {
          let position: 'first' | 'last' | 'single' | 'middle' | undefined;

          if (index === 0) position = 'first';
          if (index === allPages.length - 1) position = 'last';
          if (allPages.length === 1) position = 'single';
          if (page === '...') position = 'middle';

          return (
            <PaginationNumber
              key={page}
              href={createPageURL(page)}
              page={page}
              position={position}
              isActive={currentPage === page}
            />
          );
        })}
      </div>

      <PaginationArrow
        href={createPageURL(currentPage + 1)}
        direction={'right'}
        isDisabled={currentPage >= totalPages}/>
    </div>
  )
}

type PaginationArrowProps = {
  href: string,
  direction: 'left' | 'right',
  isDisabled?: boolean
}

function PaginationArrow(props: PaginationArrowProps) {
  const className = clsx(
    'flex h-10 w-10 items-center justify-center rounded-md border',
    {
      'pointer-events-none text-gray-300': props.isDisabled,
      'hover:bg-gray-100': !props.isDisabled,
      'mr-2 md:mr-4': props.direction === 'left',
      'ml-2 md:ml-4': props.direction === 'right',
    },
  );

  const icon =
    props.direction === 'left' ? (
      <ArrowLeftIcon className="w-4" />
    ) : (
      <ArrowRightIcon className="w-4" />
    );

  return props.isDisabled ? (
    <div className={className}>{icon}</div>
  ) : (
    <Link className={className} href={props.href}>
      {icon}
    </Link>
  );
}

type PaginationNumberProps = {
  page: number | string;
  href: string;
  position?: 'first' | 'last' | 'middle' | 'single';
  isActive: boolean;
}

function PaginationNumber(props: PaginationNumberProps) {
  const className = clsx(
    'flex h-10 w-10 items-center justify-center text-sm border',
    {
      'rounded-l-md': props.position === 'first' || props.position === 'single',
      'rounded-r-md': props.position === 'last' || props.position === 'single',
      'z-10 bg-blue-600 border-blue-600 text-white': props.isActive,
      'hover:bg-gray-100': !props.isActive && props.position !== 'middle',
      'text-gray-300': props.position === 'middle',
    },
  );

  return props.isActive || props.position === 'middle' ? (
    <div className={className}>{props.page}</div>
  ) : (
    <Link href={props.href} className={className}>
      {props.page}
    </Link>
  );
}