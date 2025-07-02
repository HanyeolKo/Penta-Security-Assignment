import { ToggleButton, ToggleButtonGroup } from "@mui/material";
import { STRATEGIES, Strategy } from "../types/strategy.ts";

export default function StrategyToggle({
  strategy,
  onChange,
}: {
  strategy: Strategy;
  onChange: (s: Strategy) => void;
}) {
  return (
    <ToggleButtonGroup
      value={strategy}
      exclusive
      onChange={(_, value) => value && onChange(value)}
      sx={{ margin: 2 }}
    >
      <ToggleButton value={STRATEGIES.PAGING}>페이징</ToggleButton>
      <ToggleButton value={STRATEGIES.INFINITY}>무한스크롤</ToggleButton>
    </ToggleButtonGroup>
  );
}
