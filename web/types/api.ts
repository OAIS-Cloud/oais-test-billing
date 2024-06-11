export interface APIContract {
  id: number;
  currency_value: number;
  invoice_closing_day: number;
  created_at: string;
  updated_at: string;
}

export interface APICurrency {
  id: number;
  code: string;
  name: string;
  created_at: string;
  updated_at: string;
}

export interface APIAnalyticsContracts {
  id: 'contracts';
  analytics: {
    date: string;
    total_contracts: number;
  }[];
}

export interface APIAnalyticsCurrencies {
  id: 'currencies';
  analytics: {
    date: string;
    total_currencies: number;
  }[];
}

export type APIAnalytics = APIAnalyticsContracts | APIAnalyticsCurrencies;
