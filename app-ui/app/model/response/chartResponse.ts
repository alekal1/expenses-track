export type ChartResponse = {
  totalSum: number;
  datasets: Dataset[];
}

type Dataset = {
  x: string;
  y: number;
}