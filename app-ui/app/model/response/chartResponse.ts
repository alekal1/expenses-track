export type ChartResponse = {
  labels: string[];
  datasets: Dataset[]
}

type Dataset = {
  label: string;
  data: number[]
}