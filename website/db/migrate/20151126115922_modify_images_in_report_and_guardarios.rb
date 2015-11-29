class ModifyImagesInReportAndGuardarios < ActiveRecord::Migration
  def change
    change_table :reports do |t|
      t.remove :images
    end
    change_table :guardarios do |t|
      t.remove :images
    end
  end
end
