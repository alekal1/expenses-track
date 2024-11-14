import ImportButton from '@/app/ui/nav/import-button';
import ExportButton from '@/app/ui/nav/export-button';

export default function NavButtons() {

  return (
    <div className={'flex justify-between'}>
      <ExportButton/>
      <ImportButton/>
    </div>
  )
}