import { lusitana } from '@/app/ui/fonts';
import { Tag } from '@/app/model/tag';
import { SubmitHandler, useForm } from 'react-hook-form';
import { toast, ToastContainer } from 'react-toastify';
import { CeResponse } from '@/app/model/response/ceResponse';
import 'react-toastify/dist/ReactToastify.css';

type Inputs = {
  tagName: string;
  amount: number;
  description: string;
}

type CreateFormProps = {
  tags: Tag[];
}

export function CreateForm(props: Readonly<CreateFormProps>) {
  const {
    register,
    reset,
    handleSubmit
  } = useForm<Inputs>()

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    fetch('/v1/expense', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: { 'Content-Type': 'application/json'},
    })
      .then(res => res.json())
      .then((ceResponse: CeResponse) => {
        if (200 === ceResponse.httpStatus) {
          toast("Expense saved!")
          reset()
        }
      })
      .catch((error) => {
        toast(`Error: ${error}`)
      })
  }

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} className={'max-w-sm mx-auto'}>
        <label htmlFor="tags" className={`${lusitana.className} block mb-2 text-sm font-normal text-gray-500`}>Select
          tag</label>
        <select id={"tags"}
                {...register('tagName')}
                className={'bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-500 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500'}>
          {props.tags.map((t) => {
            return (
              <option
                value={t.name}
                key={t.name}>
                {t.name}
              </option>
            )
          })}
        </select>
        <div>
          <label htmlFor="amount-input"
                 className={`${lusitana.className} block mt-4 mb-2 text-sm font-medium text-gray-500`}>Amount</label>
          <input type="number"
                 id="amount-input"
                 {...register("amount", {required: true})}
                 className="block w-full p-2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-xs focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-500 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>
        </div>
        <div>
          <label htmlFor="description"
                 className={`${lusitana.className} block mt-4 mb-2 text-sm font-medium text-gray-500`}>Description</label>
          <textarea id="description"
                    rows={4}
                    {...register("description", {required: true})}
                    className="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-500 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                    placeholder="Leave a description..."></textarea>
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