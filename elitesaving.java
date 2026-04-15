import React, { useState } from 'react';
import { Wallet, ArrowDownCircle, ArrowUpCircle, Target, History, Trash2 } from 'lucide-react';

export default function App() {
  const [logs, setLogs] = useState([]);
  const [amount, setAmount] = useState('');
  const [type, setType] = useState('masuk');
  const [target, setTarget] = useState('');

  // Mengira jumlah baki (Total Saldo)
  const totalBalance = logs.reduce((acc, curr) => {
    return curr.type === 'masuk' ? acc + curr.amount : acc - curr.amount;
  }, 0);

  const formatRupiah = (angka) => {
    return new Intl.NumberFormat('id-ID', {
      style: 'currency',
      currency: 'IDR',
      minimumFractionDigits: 0
    }).format(angka);
  };

  const handleAddLog = (e) => {
    e.preventDefault();
    if (!amount || amount <= 0) return;

    const newLog = {
      id: Date.now(),
      date: new Date().toLocaleDateString('id-ID', {
        day: 'numeric', month: 'short', year: 'numeric',
        hour: '2-digit', minute: '2-digit'
      }),
      amount: parseFloat(amount),
      type: type,
      target: target || (type === 'masuk' ? 'Simpanan Umum' : 'Perbelanjaan Umum')
    };

    setLogs([newLog, ...logs]);
    setAmount('');
    setTarget('');
  };

  const handleDelete = (id) => {
    setLogs(logs.filter(log => log.id !== id));
  };

  return (
    <div className="min-h-screen bg-pink-50 p-4 md:p-8 font-sans text-slate-800">
      <div className="max-w-2xl mx-auto space-y-6">
        
        {/* Header */}
        <div className="text-center space-y-2">
          <h1 className="text-3xl font-bold text-pink-600 flex items-center justify-center gap-2">
            🌸 Tabungan Elite Mikochi 🌸
          </h1>
          <p className="text-slate-500">Jejak wang masuk, keluar, dan sasaran kamu di sini!</p>
        </div>

        {/* Balance Card */}
        <div className="bg-white rounded-2xl shadow-sm border border-pink-100 p-6 flex items-center gap-4">
          <div className="p-4 bg-pink-100 text-pink-600 rounded-full">
            <Wallet size={32} />
          </div>
          <div>
            <p className="text-sm text-slate-500 font-medium">Total Saldo Semasa</p>
            <h2 className="text-3xl font-bold text-slate-800">
              {formatRupiah(totalBalance)}
            </h2>
          </div>
        </div>

        {/* Input Form */}
        <div className="bg-white rounded-2xl shadow-sm border border-pink-100 p-6">
          <h3 className="text-lg font-semibold mb-4 flex items-center gap-2 text-pink-600">
            <Target size={20} /> Rekod Transaksi Baru
          </h3>
          <form onSubmit={handleAddLog} className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {/* Type Selection */}
              <div className="space-y-2">
                <label className="text-sm font-medium text-slate-600">Jenis Transaksi</label>
                <div className="flex gap-2">
                  <button
                    type="button"
                    onClick={() => setType('masuk')}
                    className={`flex-1 py-2 px-4 rounded-xl flex items-center justify-center gap-2 transition-colors ${
                      type === 'masuk' 
                        ? 'bg-green-100 text-green-700 border-2 border-green-500 font-semibold' 
                        : 'bg-slate-50 text-slate-500 border-2 border-transparent hover:bg-slate-100'
                    }`}
                  >
                    <ArrowDownCircle size={18} /> Masuk
                  </button>
                  <button
                    type="button"
                    onClick={() => setType('keluar')}
                    className={`flex-1 py-2 px-4 rounded-xl flex items-center justify-center gap-2 transition-colors ${
                      type === 'keluar' 
                        ? 'bg-red-100 text-red-700 border-2 border-red-500 font-semibold' 
                        : 'bg-slate-50 text-slate-500 border-2 border-transparent hover:bg-slate-100'
                    }`}
                  >
                    <ArrowUpCircle size={18} /> Keluar
                  </button>
                </div>
              </div>

              {/* Amount Input */}
              <div className="space-y-2">
                <label className="text-sm font-medium text-slate-600">Jumlah (Rp)</label>
                <input
                  type="number"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  placeholder="Contoh: 50000"
                  className="w-full px-4 py-2 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-pink-400 focus:border-transparent transition-all"
                  required
                />
              </div>
            </div>

            {/* Target Input */}
            <div className="space-y-2">
              <label className="text-sm font-medium text-slate-600">Sasaran / Keterangan</label>
              <input
                type="text"
                value={target}
                onChange={(e) => setTarget(e.target.value)}
                placeholder={type === 'masuk' ? "Contoh: Wang sisa jajan, Tabung Darurat..." : "Contoh: Beli kopi, Top up game..."}
                className="w-full px-4 py-2 border border-slate-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-pink-400 focus:border-transparent transition-all"
              />
            </div>

            <button
              type="submit"
              className="w-full py-3 bg-pink-500 hover:bg-pink-600 text-white rounded-xl font-semibold shadow-sm transition-colors flex items-center justify-center gap-2"
            >
              Catat Sekarang! ✨
            </button>
          </form>
        </div>

        {/* History Section */}
        <div className="bg-white rounded-2xl shadow-sm border border-pink-100 p-6">
          <h3 className="text-lg font-semibold mb-4 flex items-center gap-2 text-pink-600">
            <History size={20} /> Sejarah Transaksi
          </h3>
          
          {logs.length === 0 ? (
            <div className="text-center py-8 text-slate-400">
              Belum ada rekod. Jom mulakan simpanan pertama hari ini!
            </div>
          ) : (
            <div className="space-y-3">
              {logs.map((log) => (
                <div key={log.id} className="flex items-center justify-between p-4 rounded-xl border border-slate-100 bg-slate-50 hover:bg-white hover:shadow-sm transition-all group">
                  <div className="flex items-center gap-4">
                    <div className={`p-2 rounded-full ${log.type === 'masuk' ? 'bg-green-100 text-green-600' : 'bg-red-100 text-red-600'}`}>
                      {log.type === 'masuk' ? <ArrowDownCircle size={20} /> : <ArrowUpCircle size={20} />}
                    </div>
                    <div>
                      <p className="font-semibold text-slate-800">{log.target}</p>
                      <p className="text-xs text-slate-400">{log.date}</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className={`font-bold ${log.type === 'masuk' ? 'text-green-600' : 'text-red-600'}`}>
                      {log.type === 'masuk' ? '+' : '-'}{formatRupiah(log.amount)}
                    </span>
                    <button 
                      onClick={() => handleDelete(log.id)}
                      className="text-slate-300 hover:text-red-500 transition-colors"
                      title="Hapus rekod"
                    >
                      <Trash2 size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

      </div>
    </div>
  );
}
