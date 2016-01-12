package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;

public class Bankiersessie extends UnicastRemoteObject implements
		IBankiersessie, RemotePublisher {

	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
	private BasicPublisher basicPublisher;

	public Bankiersessie(int reknr, IBank bank) throws RemoteException {
		laatsteAanroep = System.currentTimeMillis();
		this.reknr = reknr;
		this.bank = bank;
		String[] props = new String[1];
		this.basicPublisher = new BasicPublisher(props);

	}

	public boolean isGeldig() {
		return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public synchronized boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException {
		
		updateLaatsteAanroep();
		
		if (reknr == bestemming)
			throw new RuntimeException(
					"source and destination must be different");
		if (!bedrag.isPositive())
			throw new RuntimeException("amount must be positive");


        //basicPublisher.inform(this,reknr + "",bank.getRekening(reknr).getSaldo().getCents(), bank.getRekening(reknr).getSaldo().getCents() - bedrag.getCents());
        //basicPublisher.inform(this,bestemming + "",bank.getRekening(bestemming).getSaldo().getCents(), bank.getRekening(bestemming).getSaldo().getCents() + bedrag.getCents());

        boolean succes = bank.maakOver(reknr, bestemming, bedrag);

        if(succes){
            basicPublisher.inform(this,reknr + "", this.getRekening(),this.bank.getRekening(bestemming));
        }

		return succes;
	}

	private void updateLaatsteAanroep() throws InvalidSessionException {
		if (!isGeldig()) {
			throw new InvalidSessionException("session has been expired");
		}
		
		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException {

		updateLaatsteAanroep();
		
		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException {
		UnicastRemoteObject.unexportObject(this, true);
	}

	@Override
	public void addListener(RemotePropertyListener remotePropertyListener, String s) throws RemoteException {
        basicPublisher.addProperty(s);
        basicPublisher.addListener(remotePropertyListener,s);
	}

	@Override
	public void removeListener(RemotePropertyListener remotePropertyListener, String s) throws RemoteException {
        basicPublisher.removeProperty(s);
        basicPublisher.removeListener(remotePropertyListener,s);
	}
}
