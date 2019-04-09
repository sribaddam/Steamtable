package com.example.steamtable;

class Steamtable {
    double pressure;
    double temperature;
    double specific_volume_liquid;
    double specific_volume_gas;
    double internal_energy_liquid;
    double internal_energy_gas;
    double enthalpy_liquid;
    double vaporization;
    double enthalpy_gas;
    double entropy_liquid;
    double entropy_gas;
    public double getEnthalpy_gas() {
        return enthalpy_gas;
    }

    public double getEnthalpy_liquid() {
        return enthalpy_liquid;
    }

    public double getEntropy_gas() {
        return entropy_gas;
    }

    public double getEntropy_liquid() {
        return entropy_liquid;
    }

    public double getInternal_energy_gas() {
        return internal_energy_gas;
    }

    public double getInternal_energy_liquid() {
        return internal_energy_liquid;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSpecific_volume_gas() {
        return specific_volume_gas;
    }

    public double getSpecific_volume_liquid() {
        return specific_volume_liquid;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getVaporization() {
        return vaporization;
    }

    public void setEnthalpy_gas(double enthalpy_gas) {
        this.enthalpy_gas = enthalpy_gas;
    }

    public void setEnthalpy_liquid(double enthalpy_liquid) {
        this.enthalpy_liquid = enthalpy_liquid;
    }

    public void setEntropy_gas(double entropy_gas) {
        this.entropy_gas = entropy_gas;
    }

    public void setEntropy_liquid(double entropy_liquid) {
        this.entropy_liquid = entropy_liquid;
    }

    public void setInternal_energy_gas(double internal_energy_gas) {
        this.internal_energy_gas = internal_energy_gas;
    }

    public void setInternal_energy_liquid(double internal_energy_liquid) {
        this.internal_energy_liquid = internal_energy_liquid;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setSpecific_volume_gas(double specific_volume_gas) {
        this.specific_volume_gas = specific_volume_gas;
    }

    public void setSpecific_volume_liquid(double specific_volume_liquid) {
        this.specific_volume_liquid = specific_volume_liquid;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setVaporization(double vaporization) {
        this.vaporization = vaporization;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
