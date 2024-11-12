'use client'

import { CeResponse } from '@/app/model/response/ceResponse';
import 'react-toastify/dist/ReactToastify.css';
import { SubmitHandler, useForm } from 'react-hook-form';
import { toast, ToastContainer } from 'react-toastify';
import { lusitana } from '@/app/ui/fonts';
import { useRouter } from 'next/navigation';

type Inputs = {
  name: string
}

export function CreateForm() {
  const {
    register,
    handleSubmit
  } = useForm<Inputs>();

  const router = useRouter();

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    fetch('/v1/tag', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: { 'Content-Type': 'application/json'}
    })
      .then(res => res.json())
      .then((ceResponse: CeResponse) => {
        if (200 == ceResponse.httpStatus) {
          toast('Tag saved!')
          router.back();
        }
      })
      .catch((error) => {
        toast(`Error ${error}`)
      })
  }

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={'max-w-sm mx-auto'}>
        <div>
          <label htmlFor="name"
                 className={`${lusitana.className} block mt-4 mb-2 text-sm font-medium text-gray-500`}>Description</label>
          <textarea id="name"
                    rows={4}
                    {...register("name", {required: true})}
                    className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-500 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="Tag name..."></textarea>
        </div>
        <button type="submit"
                className={`${lusitana.className} mt-4 text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700`}>
          Save
        </button>
      </form>
      <ToastContainer
        position={'bottom-center'}
        theme={'dark'}
      />
    </>
  )
}