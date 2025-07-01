export const STRATEGIES = {
  PAGING: "paging",
  INFINITY: "infinity",
} as const;

export type Strategy = (typeof STRATEGIES)[keyof typeof STRATEGIES];
