'use client'

import ApexCharts from 'apexcharts';
import { useEffect, useState } from 'react';
import { ChartResponse } from '@/app/model/response/chartResponse';
import { CeResponse } from '@/app/model/response/ceResponse';


export function BarChart() {
  const [chartData, setChartData] = useState<ChartResponse>();

  useEffect(() => {
    async function fetchChartData() {
      const res = await fetch('/v1/chart', {
        method: 'GET'
      })
      const ceResponse: CeResponse = await res.json()

      if (200 === ceResponse.httpStatus) {
        const chartData = ceResponse.data as ChartResponse;
        setChartData(chartData)
      }
    }
    fetchChartData()

  }, []);

  function initChart(node: HTMLDivElement) {
    if (!chartData) {
      return;
    }

    const options = {
      colors: ['#FDBA8C'],
      series: [
        {
          name: 'Total',
          color: '#FDBA8C',
          data: chartData.datasets
        },
      ],
      chart: {
        type: 'bar',
        height: '320px',
        fontFamily: 'Inter, sans-serif',
        toolbar: {
          show: false,
        },
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '70%',
          borderRadiusApplication: 'end',
          borderRadius: 8,
        },
      },
      tooltip: {
        shared: true,
        intersect: false,
        style: {
          fontFamily: 'Inter, sans-serif',
        },
      },
      states: {
        hover: {
          filter: {
            type: 'darken',
            value: 1,
          },
        },
      },
      stroke: {
        show: true,
        width: 0,
        colors: ['transparent'],
      },
      grid: {
        show: false,
        strokeDashArray: 4,
        padding: {
          left: 2,
          right: 2,
          top: -14
        },
      },
      dataLabels: {
        enabled: false,
      },
      legend: {
        show: false,
      },
      xaxis: {
        floating: false,
        labels: {
          show: true,
          style: {
            fontFamily: 'Inter, sans-serif',
            cssClass: 'text-xs font-normal fill-gray-500 dark:fill-gray-400'
          }
        },
        axisBorder: {
          show: false,
        },
        axisTicks: {
          show: false,
        },
      },
      yaxis: {
        show: false,
      },
      fill: {
        opacity: 1,
      },
    }

    if (typeof ApexCharts !== 'undefined') {
      const chart = new ApexCharts(node, options);
      chart.render();
    }
  }


  if (!chartData) {
    return (
      <h1>Loading...</h1>
    )
  }

  return (
    <div className="w-full bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6">
      <div className="grid grid-cols-2">
        <dl className="flex items-center">
          <dt className="text-gray-500 dark:text-gray-400 text-sm font-normal me-1">Money spent:</dt>
          <dd className="text-gray-900 text-sm dark:text-white font-semibold">€{chartData.totalSum}</dd>
        </dl>
      </div>

      <div
        ref={node => {
          if (node) {
            initChart(node)
          }
        }}
        id="column-chart">

      </div>

    </div>
  )
}