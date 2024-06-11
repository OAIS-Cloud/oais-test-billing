export function getAllDaysFromAgoAsDateString(days: number) {
  const dates: string[] = [];
  for (let i = 0; i < days; i++) {
    const date = new Date();
    date.setDate(date.getDate() - i);
    dates.push(date.toISOString().split('T')[0]);
  }

  return dates;
}

export function dateToCleanFileNameDateFormat(date: Date) {
  return date
    .toISOString()
    .replace(/^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.\d{3}Z$/, '$1$2$3_$4$5$6');
}
