## Main -> Pump { PumpOn, PumpOff}
## Main -> FlowMeter {Reset}
## Main -> Screen {*MarkDown*}
## Main -> CardCompany {CardInfo}
## Main -> GasStation {TransactionInfo, Errors}
## Hose -> Main {ConToCar, ConTo Pump, NotCon, TankFull}
## CCReader -> Main {SendCard}
## GasStation -> Main {GasPrices, AvailableGas}
## FlowMeter -> Main {GasVolume}