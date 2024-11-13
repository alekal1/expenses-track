'use client'

import { lusitana } from '@/app/ui/fonts';
import React, { useEffect, useState } from 'react';
import "chart.js/auto";
import { Bar } from 'react-chartjs-2';
import { ChartResponse } from '@/app/model/response/chartResponse';
import { CeResponse } from '@/app/model/response/ceResponse';

export default function Home() {
  const [chartData, setChartData] = useState<ChartResponse>();

  useEffect(() => {
    async function fetchChartData() {
      const res = await fetch('/v1/chart', {
        method: 'GET'
      })
      const ceResponse: CeResponse = await res.json()

      setChartData(ceResponse.data as ChartResponse)
    }

    fetchChartData()
  }, []);


  const chartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top'
      }
    }
  }

  if (!chartData) {
    return (
      <h1>Loading...</h1>
    )
  }


  return (
    <main>
      <h1 className={`${lusitana.className} mb-4 text-xl md:text-2xl`}>
        Dashboard
      </h1>
      <Bar options={chartOptions} data={chartData}/>
    </main>
  );
}
