import { Expense } from '@/app/model/expense';

export type SearchExpensesResponse = {
  content: Expense[];
  totalPages: number;
  totalElements: number;
  pageNumber: number;
  pageSize: number;
}