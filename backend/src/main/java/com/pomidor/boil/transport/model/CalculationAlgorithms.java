package com.pomidor.boil.transport.model;

import com.pomidor.boil.transport.dtos.*;
import com.pomidor.boil.transport.exceptions.NotFoundByIdException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculationAlgorithms {
    public static TransportDto calculateTransport(TransportInputDto input) {
        int demandAmount = 0;
        int supplyAmount = 0;
        int realSupplierAmount = input.suppliers().size();
        int realRecipientAmount = input.recipients().size();

        for (RecipientDto recipient : input.recipients()) {
            demandAmount += recipient.demand();
        }
        for (SupplierDto supplier : input.suppliers()) {
            supplyAmount += supplier.supply();
        }
        Boolean supplyDemandEquality = demandAmount == supplyAmount;

        List<List<RecipientToSupplierData>> recipientSupplierTable = new ArrayList<>();

        //Adding suppliers and recipients to table
        for (SupplierDto supplier : input.suppliers()) {
            List<RecipientToSupplierData> tempTable = new ArrayList<>();
            for (RecipientDto recipient : input.recipients()) {
                TransportCostDto transportCost = getTransportCostById(input.transportCosts(), supplier.id(), recipient.id());
                tempTable.add(new RecipientToSupplierData(supplier.id(), recipient.id(), recipient.unitPrice(), transportCost.cost(), supplier.unitPrice()));
            }
            if (!supplyDemandEquality) {
                tempTable.add(new RecipientToSupplierData(supplier.id(), Integer.MAX_VALUE, 0, true));
            }
            recipientSupplierTable.add(tempTable);
        }
        //Adding fictional recipient and supplier if needed
        if (!supplyDemandEquality) {
            List<RecipientToSupplierData> tempTable = new ArrayList<>();
            for (RecipientDto recipient : input.recipients()) {
                tempTable.add(new RecipientToSupplierData(Integer.MAX_VALUE, recipient.id(), 0, true));
            }
            tempTable.add(new RecipientToSupplierData(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, true));
            recipientSupplierTable.add(tempTable);
        }

        //Making two list containing amount of supply and demand for each supplier and recipient
        List<Integer> demandList = new ArrayList<>();
        List<Integer> supplyList = new ArrayList<>();

        for (RecipientDto recipient : input.recipients()) {
            demandList.add(recipient.demand());
        }
        for (SupplierDto supplier : input.suppliers()) {
            supplyList.add(supplier.supply());
        }
        if (!supplyDemandEquality) {
            demandList.add(supplyAmount);
            supplyList.add(demandAmount);
        }

        //Making first iteration of product amount by maximum profit element
        Boolean demandEmpty = false;
        IdsWithValue lowestProfit = findLowestProfit(recipientSupplierTable);
        while (true) {
            int maxProfitRow = lowestProfit.row(), maxProfitColumn = lowestProfit.column();
            double maxProfit = lowestProfit.value();
            for (int i = 0; i < realSupplierAmount; i++) {
                if (supplyList.get(i) != 0) {
                    for (int j = 0; j < realRecipientAmount; j++) {
                        if (demandList.get(j) != 0 && recipientSupplierTable.get(i).get(j).getProductAmount() == 0 &&
                                recipientSupplierTable.get(i).get(j).getProfit() >= maxProfit) {
                            maxProfit = recipientSupplierTable.get(i).get(j).getProfit();
                            maxProfitRow = i;
                            maxProfitColumn = j;
                        }
                    }
                }
            }

            int amountToSupply = Integer.min(demandList.get(maxProfitColumn), supplyList.get(maxProfitRow));
            recipientSupplierTable.get(maxProfitRow).get(maxProfitColumn).setProductAmount(amountToSupply);
            demandList.set(maxProfitColumn, demandList.get(maxProfitColumn) - amountToSupply);
            supplyList.set(maxProfitRow, supplyList.get(maxProfitRow) - amountToSupply);


            int demandLeft = 0, supplyLeft = 0;
            for (int i = 0; i < realSupplierAmount; i++) {
                supplyLeft += supplyList.get(i);
            }
            for (int i = 0; i < realRecipientAmount; i++) {
                demandLeft += demandList.get(i);
            }
            if (demandLeft == 0 || supplyLeft == 0) {
                if (demandLeft == 0) {
                    demandEmpty = true;
                }
                break;
            }
        }
        //If supply doesn't equal demand rest of supply or demand is added to fictional supplier or recipient
        if (!supplyDemandEquality) {
            if (demandEmpty) {
                for (int i = 0; i < realSupplierAmount; i++) {
                    if (supplyList.get(i) != 0) {
                        recipientSupplierTable.get(i).get(realRecipientAmount).setProductAmount(supplyList.get(i));
                        demandList.set(realRecipientAmount, demandList.get(i) - supplyList.get(i));
                        supplyList.set(i, 0);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < realRecipientAmount; i++) {
                    if (demandList.get(i) != 0) {
                        recipientSupplierTable.get(realSupplierAmount).get(i).setProductAmount(demandList.get(i));
                        supplyList.set(realSupplierAmount, supplyList.get(i) - demandList.get(i));
                        demandList.set(i, 0);
                        break;
                    }
                }
            }
            recipientSupplierTable.get(realSupplierAmount).get(realRecipientAmount).setProductAmount(supplyList.get(realSupplierAmount));
        }

        //Main iteration loop
        while (true) {
            List<Double> alfas = Arrays.asList(new Double[supplyList.size()]);
            List<Double> betas = Arrays.asList(new Double[demandList.size()]);
            alfas.set(0, 0.0);

            //Calculating dual variables alfa and beta
            Boolean dualVarEmpty = true;
            while (dualVarEmpty) {
                for (int i = 0; i < alfas.size(); i++) {
                    if (alfas.get(i) != null) {
                        for (int j = 0; j < recipientSupplierTable.get(i).size(); j++) {
                            if (recipientSupplierTable.get(i).get(j).getBase() && betas.get(j) == null) {
                                betas.set(j, recipientSupplierTable.get(i).get(j).getProfit() - alfas.get(i));
                            }
                        }
                    }
                }
                for (int j = 0; j < betas.size(); j++) {
                    if (betas.get(j) != null) {
                        for (int i = 0; i < recipientSupplierTable.size(); i++) {
                            if (recipientSupplierTable.get(i).get(j).getBase() && alfas.get(i) == null) {
                                alfas.set(i, recipientSupplierTable.get(i).get(j).getProfit() - betas.get(j));
                            }
                        }
                    }
                }
                dualVarEmpty = false;
                for (Double alfa : alfas) {
                    if (alfa == null) {
                        dualVarEmpty = true;
                        break;
                    }
                }
                for (Double beta : betas) {
                    if (beta == null) {
                        dualVarEmpty = true;
                        break;
                    }
                }
            }
            //Calculating delta and finding maximum
            double maxDelta = 0;
            int maxDeltaRow = -1, maxDeltaColumn = -1;
            for (int i = 0; i < recipientSupplierTable.size(); i++) {
                for (int j = 0; j < recipientSupplierTable.get(i).size(); j++) {
                    recipientSupplierTable.get(i).get(j).setDelta(alfas.get(i), betas.get(j));
                    if (recipientSupplierTable.get(i).get(j).getDelta() > maxDelta) {
                        maxDelta = recipientSupplierTable.get(i).get(j).getDelta();
                        maxDeltaRow = i;
                        maxDeltaColumn = j;
                    }
                }
            }
            //If one of deltas is positive then make a cycle and add/subtract product amounts, if no delta is positive break the loop
            if (maxDelta > 0) {
                for (int j = 0; j < recipientSupplierTable.get(maxDeltaRow).size(); j++) {
                    Boolean cycleCompleted = false;
                    if (j != maxDeltaColumn && recipientSupplierTable.get(maxDeltaRow).get(j).getBase()) {
                        for (int i = 0; i < recipientSupplierTable.size(); i++) {
                            if (i != maxDeltaRow && recipientSupplierTable.get(i).get(j).getBase() && recipientSupplierTable.get(i).get(maxDeltaColumn).getBase()) {
                                int productAmount = Integer.min(recipientSupplierTable.get(maxDeltaRow).get(j).getProductAmount(), recipientSupplierTable.get(i).get(maxDeltaColumn).getProductAmount());
                                recipientSupplierTable.get(maxDeltaRow).get(maxDeltaColumn).addProductAmount(productAmount);
                                recipientSupplierTable.get(maxDeltaRow).get(j).subtractProductAmount(productAmount);
                                recipientSupplierTable.get(i).get(j).addProductAmount(productAmount);
                                recipientSupplierTable.get(i).get(maxDeltaColumn).subtractProductAmount(productAmount);
                                cycleCompleted = true;
                                break;
                            }
                        }
                    }
                    if (cycleCompleted) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return transformToTransportDto(recipientSupplierTable);
    }

    private static RecipientDto getRecipientById(List<RecipientDto> recipientList, int id) {
        for (RecipientDto recipientDto : recipientList) {
            if (recipientDto.id() == id) {
                return recipientDto;
            }
        }
        throw new NotFoundByIdException();
    }

    private static TransportCostDto getTransportCostById(List<TransportCostDto> transportCostList, int supplierId, int recipientId) {
        for (TransportCostDto transportCost : transportCostList) {
            if (transportCost.recipientId() == recipientId && transportCost.supplierId() == supplierId) {
                return transportCost;
            }
        }
        throw new NotFoundByIdException();
    }

    private static IdsWithValue findLowestProfit(List<List<RecipientToSupplierData>> recipientToSupplierTable) {
        double lowestProfit = recipientToSupplierTable.get(0).get(0).getProfit();
        int row = 0, column = 0;
        for (int i = 0; i < recipientToSupplierTable.size(); i++) {
            for (int j = 0; j < recipientToSupplierTable.get(i).size(); j++) {
                if (recipientToSupplierTable.get(i).get(j).getProfit() < lowestProfit) {
                    row = i;
                    column = j;
                    lowestProfit = recipientToSupplierTable.get(i).get(j).getProfit();
                }
            }
        }
        return new IdsWithValue(row, column, lowestProfit);
    }

    private record IdsWithValue(int row, int column, double value) {
    }
    private static TransportDto transformToTransportDto(List<List<RecipientToSupplierData>> recipientSupplierTable){
        List<UnitIncomeDto> unitIncomes = new ArrayList<>();
        List<TransactionDto> transactions = new ArrayList<>();
        Double totalCost = 0.0,
                totalIncome =0.0,
                totalProfit =0.0;

        for (List<RecipientToSupplierData> recipientToSupplierList : recipientSupplierTable) {
            for (RecipientToSupplierData recipientToSupplierData : recipientToSupplierList) {
                unitIncomes.add(new UnitIncomeDto(recipientToSupplierData.getSupplierId(), recipientToSupplierData.getRecipientId(), recipientToSupplierData.getProfit()));
                transactions.add(new TransactionDto(recipientToSupplierData.getSupplierId(), recipientToSupplierData.getRecipientId(), recipientToSupplierData.getProductAmount()));
                totalProfit+=recipientToSupplierData.getProfit()*recipientToSupplierData.getProductAmount();
                totalIncome+=recipientToSupplierData.getSellingPrice()*recipientToSupplierData.getProductAmount();
                totalCost+=recipientToSupplierData.getCosts()*recipientToSupplierData.getProductAmount();
            }
        }
        return new TransportDto(unitIncomes, transactions, totalCost, totalIncome, totalProfit);
    }
}
