'use client'
import { CloseIcon } from 'next/dist/client/components/react-dev-overlay/internal/icons/CloseIcon';
import { ArrowUpOnSquareIcon } from '@heroicons/react/24/outline';
import { useState } from 'react';
import { CeResponse } from '@/app/model/response/ceResponse';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function ImportButton() {
  const [showModal, setShowModal] = useState<boolean>(false);
  const [selectedFile, setSelectedFile] = useState<File | null>();

  function closeModal() {
    setSelectedFile(null);
    setShowModal(false);
  }

  function importFile() {
    if (!selectedFile) {
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    fetch("/v1/management", {
      method: 'POST',
      body: formData
    })
      .then((response) => response.json())
      .then((response: CeResponse) => {
        if (200 === response.httpStatus) {
          toast(response.message)
          setSelectedFile(null);
          setShowModal(false);
        } else {
          toast('Error with importing data!')
        }
      })
  }

  return (
    <>
      <ToastContainer
        position={'bottom-center'}
        theme={'dark'}/>
      <button
        type={'button'}
        onClick={() => setShowModal(true)}
        className={'flex h-[48px] grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 text-sm font-medium hover:bg-sky-100 hover:text-blue-600 md:flex-none md:justify-start md:p-2 md:px-3'}
      >
        <ArrowUpOnSquareIcon className="w-6"/>
        <p className="hidden md:block">Import</p>
      </button>
      {showModal ? (
        <div tabIndex={-1} aria-hidden="true"
             className={'content-center overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full'}>
          <div className={'relative p-4 w-full max-w-2xl max-h-full'}>
            <div className={'relative bg-white rounded-lg shadow dark:bg-gray-700'}>
              <div className={'flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600'}>
                <h3 className={'text-xl font-semibold text-gray-900 dark:text-white'}>
                  Import data
                </h3>
                <button type="button"
                        onClick={() => closeModal()}
                        className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                        data-modal-hide="default-modal">
                  <CloseIcon/>
                  <span className="sr-only">Close modal</span>
                </button>
              </div>
              <div className="p-4 md:p-5 space-y-4">
                <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
                  Only valid json file can be imported!
                </p>
                <div className="flex items-center justify-center w-full">
                  {selectedFile
                    ? <h1 className={'text-xl font-semibold text-gray-900 dark:text-white'}>{selectedFile.name}</h1>
                    : <label htmlFor="dropzone-file"
                             className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500">
                      <div className="flex flex-col items-center justify-center pt-5 pb-6">
                        <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span className="font-semibold">Click to upload</span> or
                          drag and drop</p>
                        <p className="text-xs text-gray-500 dark:text-gray-400">JSON</p>
                      </div>
                      <input id="dropzone-file"
                             type="file"
                             className="hidden"
                             accept={"application/json"}
                             onChange={(event) => setSelectedFile(event.target.files?.item(0))}/>
                    </label>}

                </div>
              </div>
              <div className="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
                <button type="button"
                        onClick={() => importFile()}
                        className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                  Import
                </button>
                <button type="button"
                        onClick={() => closeModal()}
                        className="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                  Decline
                </button>
              </div>
            </div>
          </div>
        </div>
      ) : null}
    </>
  )
}